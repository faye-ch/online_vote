package com.two.vote.dao;



import com.two.vote.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserMapper {
    public User queryByEmail(String email);

    public User queryById(Integer userid);

    public void save(User user);

    public void updateType1(Integer userid);
    public void updateType0(Integer userid);

    public void updateUser(User user);

    public void updatePassword(@Param("email") String email,@Param("password") String password);

    public void updateUsername(@Param("username") String username, @Param("email") String email);
}
