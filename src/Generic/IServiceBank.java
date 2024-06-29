package Generic;

import java.util.Optional;

public interface IServiceBank<T> {
    Optional<T> findById(int id);
}
