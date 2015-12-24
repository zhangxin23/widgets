package net.coderland.server.core.dao.mapper;

import java.util.List;
import net.coderland.server.core.model.pojo.StockCode;
import net.coderland.server.core.model.pojo.StockCodeExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface StockCodeMapper {
    int countByExample(StockCodeExample example);

    int deleteByExample(StockCodeExample example);

    @Delete({
        "delete from stock_code",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into stock_code (name, code, ",
        "house, status)",
        "values (#{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, ",
        "#{house,jdbcType=TINYINT}, #{status,jdbcType=TINYINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(StockCode record);

    int insertSelective(StockCode record);

    List<StockCode> selectByExample(StockCodeExample example);

    @Select({
        "select",
        "id, name, code, house, status",
        "from stock_code",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    StockCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StockCode record, @Param("example") StockCodeExample example);

    int updateByExample(@Param("record") StockCode record, @Param("example") StockCodeExample example);

    int updateByPrimaryKeySelective(StockCode record);

    @Update({
        "update stock_code",
        "set name = #{name,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=VARCHAR},",
          "house = #{house,jdbcType=TINYINT},",
          "status = #{status,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(StockCode record);
}