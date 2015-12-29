package net.coderland.server.core.dao.mapper;

import java.util.List;
import net.coderland.server.core.model.pojo.StockFollows;
import net.coderland.server.core.model.pojo.StockFollowsExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface StockFollowsMapper {
    int countByExample(StockFollowsExample example);

    int deleteByExample(StockFollowsExample example);

    @Delete({
        "delete from stock_follows",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into stock_follows (user, stock_code, ",
        "ctime, status)",
        "values (#{user,jdbcType=VARCHAR}, #{stockCode,jdbcType=VARCHAR}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{status,jdbcType=TINYINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(StockFollows record);

    int insertSelective(StockFollows record);

    List<StockFollows> selectByExample(StockFollowsExample example);

    @Select({
        "select",
        "id, user, stock_code, ctime, status",
        "from stock_follows",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    StockFollows selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StockFollows record, @Param("example") StockFollowsExample example);

    int updateByExample(@Param("record") StockFollows record, @Param("example") StockFollowsExample example);

    int updateByPrimaryKeySelective(StockFollows record);

    @Update({
        "update stock_follows",
        "set user = #{user,jdbcType=VARCHAR},",
          "stock_code = #{stockCode,jdbcType=VARCHAR},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StockFollows record);
}