package net.wth.productobserver.repo;

import net.wth.productobserver.domain.ObserveredPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObserveredPageRepo extends JpaRepository<ObserveredPage, Long> {
}
