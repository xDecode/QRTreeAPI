package org.marv.repository;

import org.marv.entities.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {

    @Modifying
    @Query("UPDATE QRCode q SET q.isActive = false WHERE q.id = :id")
    void deactivateQRCode(@Param("id") Long id);

    void deleteById(long id);

    @Modifying
    @Query("UPDATE QRCode q SET q.isActive = true WHERE q.id = :id")
    void activateQRCode(@Param("id") Long id);

    List<QRCode> findByAccount_Id(String accountId);


}