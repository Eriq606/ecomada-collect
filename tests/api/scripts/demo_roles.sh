#!/bin/bash
BASE_URL="http://localhost:8090"

echo "--- 1. Authentification & Roles ---"
ADMIN_TOKEN=$(curl -s -X POST $BASE_URL/api/auth/register -H "Content-Type: application/json" -d '{"nom": "Admin User", "email": "admin-demo@admin.mg", "motDePasse": "pass"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
CITO_TOKEN=$(curl -s -X POST $BASE_URL/api/auth/register -H "Content-Type: application/json" -d '{"nom": "Cito User", "email": "cito-demo@ecomada.mg", "motDePasse": "pass"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
COLL_TOKEN=$(curl -s -X POST $BASE_URL/api/auth/register -H "Content-Type: application/json" -d '{"nom": "Coll User", "email": "coll-demo@collecteur.mg", "motDePasse": "pass"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
RECY_TOKEN=$(curl -s -X POST $BASE_URL/api/auth/register -H "Content-Type: application/json" -d '{"nom": "Recy User", "email": "recy-demo@recycleur.mg", "motDePasse": "pass"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

echo "Admin Token: ${ADMIN_TOKEN:0:20}..."

echo -e "\n--- 2. Test Role ADMIN (Acces a la liste des utilisateurs) ---"
curl -s -o /dev/null -w "Status Admin sur /users: %{http_code}\n" -X GET $BASE_URL/api/users -H "Authorization: Bearer $ADMIN_TOKEN"
curl -s -o /dev/null -w "Status Citizen sur /users: %{http_code} (Attendu: 403)\n" -X GET $BASE_URL/api/users -H "Authorization: Bearer $CITO_TOKEN"

echo -e "\n--- 3. Test Workflow CITOYEN (Depot) ---"
DEPOT_ID=$(curl -s -X POST $BASE_URL/api/depots -H "Content-Type: application/json" -H "Authorization: Bearer $CITO_TOKEN" \
-d '{"poidsKg": 15.0, "userId": 6, "pointCollecteId": 1, "typeDechetId": 1}' | grep -o '"id":[0-9]*' | cut -d':' -f2)
echo "Depot cree avec ID: $DEPOT_ID"

echo -e "\n--- 4. Test Workflow COLLECTEUR (Collecte) ---"
curl -s -o /dev/null -w "Status Collecte: %{http_code}\n" -X PATCH "$BASE_URL/api/depots/$DEPOT_ID/status?statusId=2&collecteurId=1" -H "Authorization: Bearer $COLL_TOKEN"

echo -e "\n--- 5. Test Workflow RECYCLEUR (Valorisation) ---"
curl -s -o /dev/null -w "Status Valorisation: %{http_code}\n" -X PATCH "$BASE_URL/api/depots/$DEPOT_ID/status?statusId=4&recycleurId=1" -H "Authorization: Bearer $RECY_TOKEN"

echo -e "\n--- 6. Verification Impact CITOYEN Final ---"
curl -s $BASE_URL/api/users/6/impact -H "Authorization: Bearer $CITO_TOKEN"
echo -e "\n"
