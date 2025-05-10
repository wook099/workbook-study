package sw_workbook.spring.config.jwt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_workbook.spring.config.jwt.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUsername(String username);//username은 이메일이야

    void deleteByUsername(String username);



}
