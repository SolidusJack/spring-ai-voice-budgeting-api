package solidusjack.budgeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solidusjack.budgeting.model.Gasto;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
}