package com.wacajou.data.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wacajou.data.jpa.domain.LettreDeRecommandation;
import com.wacajou.data.jpa.domain.Reason;
import com.wacajou.data.jpa.domain.Statut;
import com.wacajou.data.jpa.domain.User;

public interface LettreDeRecommandationRepository extends
		JpaRepository<LettreDeRecommandation, Long> {

	public static final String FIND_BY_USER_SENDER = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userSender = :user ";
	public static final String FIND_BY_USER_RECIVER = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user ";
	public static final String FIND_BY_USER_RECIVER_STATUT = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user " 
			+ "AND ldm.statut = :statut ";
	public static final String FIND_BY_USER_RECIVER_REASON = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user " 
			+ "AND ldm.reason = :reason ";
	public static final String FIND_BY_USER_RECIVER_STATUT_REASON = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user "
			+ "AND ldm.statut = :statut "
			+ "AND ldm.reason = :reason ";
	public static final String FIND_BY_USER_SENDER_RECIVER = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user_reciver "
			+ "AND ldm.userSender = :user_sender ";
	public static final String FIND_BY_USER_SENDER_RECIVER_NOT_COMPLETE = "SELECT ldm "
			+ "FROM LettreDeRecommandation ldm "
			+ "WHERE ldm.userReciver = :user_reciver "
			+ "AND ldm.userSender = :user_sender "
			+ "AND ldm.statut != '0' ";

	Page<LettreDeRecommandation> findAll(Pageable page);

	@Query(FIND_BY_USER_SENDER)
	Page<LettreDeRecommandation> findByUserSender(@Param("user") User user,
			Pageable pageable);

	@Query(FIND_BY_USER_RECIVER)
	Page<LettreDeRecommandation> findByUserReciver(@Param("user") User user,
			Pageable pageable);

	@Query(FIND_BY_USER_RECIVER_STATUT)
	Page<LettreDeRecommandation> findByUserReciverStatut(
			@Param("user") User user, @Param("statut") Statut statut,
			Pageable pageable);

	@Query(FIND_BY_USER_RECIVER_REASON)
	Page<LettreDeRecommandation> findByUserReciverReason(
			@Param("user") User user, @Param("reason") Reason reason,
			Pageable pageable);

	@Query(FIND_BY_USER_RECIVER_STATUT_REASON)
	Page<LettreDeRecommandation> findByUserReciverStatutReason(
			@Param("user") User user, @Param("statut") Statut statut,
			@Param("reason") Reason reason, Pageable pageable);

	@Query(FIND_BY_USER_SENDER_RECIVER)
	Page<LettreDeRecommandation> findByUserSenderReciver(
			@Param("user_sender") User userSender,
			@Param("user_reciver") User userReciver, Pageable pageable);

	@Query(FIND_BY_USER_SENDER_RECIVER_NOT_COMPLETE)
	Page<LettreDeRecommandation> findByUserSenderReciverNotComplete(
			@Param("user_sender") User userSender,
			@Param("user_reciver") User userReciver, Pageable pageable);
	
}
