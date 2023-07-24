package com.example.shardingjdbc.shardingAlgorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Properties;

public class ShopStarndSA  implements StandardShardingAlgorithm {


    // 实现精确查询的方法（in、=查询会调用方法）
    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        //获取逻辑表明
        String logicTableName = preciseShardingValue.getLogicTableName();
        // 获取路由键：psv.getColumnName()
        String columnName = preciseShardingValue.getColumnName();
        // 获取本次SQL语句中具体的路由键值
        Long  shopingId = (Long) preciseShardingValue.getValue();
        //通过获取到的ID值对2取模，计算出目标表的后缀
        BigDecimal target = BigDecimal.valueOf(shopingId % 2);
        // 拼接上逻辑表名作为前缀，得到最终的目标表名
        String targetTable = logicTableName + "_0" + target;
        if (collection.contains(targetTable)) {
            return targetTable;
        }
        throw new UnsupportedOperationException(targetTable +
                "表在逻辑库中不存在，请检查你的SQL语句或数据节点配置...");
    }

    // 实现范围查询的方法（BETWEEN AND、>、<、>=、<=会调用的方法）
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        return null;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties props) {
        System.out.println("正在使用自定义的starnd分片算法");
    }
}
