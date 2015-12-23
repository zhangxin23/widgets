package net.coderland.server.core.dao.mapper;

import java.util.List;
import net.coderland.server.core.model.pojo.Stock;
import net.coderland.server.core.model.pojo.StockExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface StockMapper {
    int countByExample(StockExample example);

    int deleteByExample(StockExample example);

    @Delete({
        "delete from stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into stock (name, code, ",
        "price, ctime, unit)",
        "values (#{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=INTEGER}, #{ctime,jdbcType=BIGINT}, #{unit,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    @Select({
        "select",
        "id, name, code, price, ctime, unit",
        "from stock",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Stock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    @Update({
        "update stock",
        "set name = #{name,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "ctime = #{ctime,jdbcType=BIGINT},",
          "unit = #{unit,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Stock record);
}