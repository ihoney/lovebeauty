package com.lb.dao;

import com.lb.bean.Ad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-26
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AdDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public int getAdCount() {
        String sql = "select count(*) from advertisement";
        return jdbcTemplate.queryForInt(sql);
    }

    public List<Map<String, Object>> getAdByPage(int pageIndex, int pageSize) {
        String sql =
                "SELECT " +
                        " a.id, " +
                        " a.type, " +
                        " a.picName, " +
                        " a.`backup`, " +
                        " a.state, " +
                        " CASE a.type " +
                        "WHEN '外部链接' THEN " +
                        " a.url " +
                        "WHEN '内部链接' THEN " +
                        " ( " +
                        "  SELECT " +
                        "   CONCAT( " +
                        "    \"城市:\", " +
                        "    c.`name`, " +
                        "    \" 商铺:\", " +
                        "    s. NAME, " +
                        "    \" 作品:\", " +
                        "    d. NAME " +
                        "   ) " +
                        "  FROM " +
                        "   demo d, " +
                        "   city c, " +
                        "   seller_validate_info s " +
                        "  WHERE " +
                        "   d.id = a.url " +
                        "  AND c.id = a.cityId " +
                        "  AND s.sellerId = a.sellerId " +
                        " ) " +
                        "END AS url " +
                        "FROM " +
                        " advertisement a order by a.state desc limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        return jdbcTemplate.queryForList(sql);
    }

    public void changeAdState(String adId, String state) {
        String sql = " update advertisement set state = '" + state + "' where id = " + adId;
        jdbcTemplate.update(sql);
    }

    public void addAd(Ad ad) {
        String sql;
        if ("外部链接".equals(ad.getType())) {
            sql = "INSERT INTO advertisement (type, picName, url, BACKUP) VALUE (?,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{ad.getType(), ad.getPicName(), ad.getUrl(), ad.getBackup()});
        } else {
            sql = "INSERT INTO advertisement (type, picName, url,cityId,sellerId, BACKUP) VALUE (?,?,?,?,?,?)";
            jdbcTemplate.update(sql, new Object[]{ad.getType(), ad.getPicName(), ad.getUrl(), ad.getCityId(), ad.getSellerId(), ad.getBackup()});
        }
    }

    public List<Map<String, Object>> getAdById(String adId) {
        String sql = "select * from advertisement where id = " + adId;
        return jdbcTemplate.queryForList(sql);
    }

    public void updateAd(Ad ad) {
        String sql = "update advertisement set type=?,url=?,picName=?,backup=? where id=?";
        jdbcTemplate.update(sql, new Object[]{ad.getType(), ad.getUrl(), ad.getPicName(), ad.getBackup(), ad.getId()});
    }

    public void deleteAd(String adId) {
        String sql = "delete from advertisement where id = " + adId;
        jdbcTemplate.update(sql);
    }

    public List<Map<String, Object>> queryAdsMobile() {
        String sql = "SELECT * from advertisement a where a.state = '启用' and a.type = '内部链接' order by id desc limit 8 ";
        return jdbcTemplate.queryForList(sql);
    }
}
