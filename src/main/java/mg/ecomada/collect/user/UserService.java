package mg.ecomada.collect.user;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto create(UserDto dto);

    UserDto update(Long id, UserDto dto);

    void delete(Long id);

    Map<String, Object> getImpact(Long id);
}
