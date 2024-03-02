package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.rsa.dto.UserPageDTO;
import com.rsa.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getByUsername(@Param("username") String username);

    @Select("select * from user where id = #{id}")
    User getById(Integer id);

    int updateById(User user);

    Page<User> pageQuery(UserPageDTO userPageDTO);

    @Delete("delete from user where id = #{id}")
    void deleteById(User user);

    Page<User> adminPageQuery(UserPageDTO userPageDTO);

    @Select("select * from user where last_login_time is not null and last_login_time <= #{time} and status = 1")
    List<User> filterByLastLoginTime(LocalDateTime time);

    @Select("update user set status = 0 where id = #{id}")
    void updateStopStatus(User user);

    @Select("select * from user where email = #{email}")
    User getByEmail(String email);
}
