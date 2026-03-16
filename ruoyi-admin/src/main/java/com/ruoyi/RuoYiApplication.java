package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import org.springframework.boot.CommandLineRunner;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  木材管理系统启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }

    @Bean
    public CommandLineRunner run(DataSource dataSource, ResourceLoader resourceLoader) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                Resource resource = resourceLoader.getResource("classpath:sql/update_menu.sql");
                String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                String[] sqlStatements = sql.split(";");
                try (Statement stmt = conn.createStatement()) {
                    for (String statement : sqlStatements) {
                        if (!statement.trim().isEmpty()) {
                            stmt.execute(statement);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to execute update_menu.sql: " + e.getMessage());
            }
        };
    }
}
