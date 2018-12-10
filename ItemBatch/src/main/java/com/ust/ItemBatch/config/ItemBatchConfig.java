package com.ust.ItemBatch.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ust.ItemBatch.ItemBatchProcessor;
import com.ust.ItemBatch.pojo.ReceivedItems;

@Configuration
@EnableBatchProcessing
public class ItemBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public DataSource dataSource(@Value("${spring.datasource.url}") String dataSourceUrl,
			@Value("${spring.datasource.driverClassName}") String driverClassName,
			@Value("${spring.datasource.username}") String userName,
			@Value("${spring.datasource.password}") String password) {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dataSourceUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public ItemBatchProcessor processor() {
		return new ItemBatchProcessor();
	}

	@Bean
	public JdbcCursorItemReader<ReceivedItems> reader() {
		JdbcCursorItemReader<ReceivedItems> reader = new JdbcCursorItemReader<ReceivedItems>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT item_nbr,rcvd_qty,po_Num from received_items");
		reader.setRowMapper(new ReceivedItemsRowMapper());
		return reader;
	}

	public class ReceivedItemsRowMapper implements RowMapper<ReceivedItems> {

		public ReceivedItems mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReceivedItems rcvItems = new ReceivedItems();
			rcvItems.setItemNumber(rs.getInt(1));
			rcvItems.setRcvdQty(rs.getInt(2));
			rcvItems.setPoNum(rs.getInt(3));
			return rcvItems;
		}

	}

	@Bean
	public FlatFileItemWriter<ReceivedItems> writer() {
		FlatFileItemWriter<ReceivedItems> writer = new FlatFileItemWriter<ReceivedItems>();
		writer.setResource(new ClassPathResource("/receivedItems.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<ReceivedItems>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<ReceivedItems>() {
					{
						setNames(new String[] { "itemNumber", "rcvdQty", "poNum" });
					}
				});
			}
		});
		writer.open(new ExecutionContext());
		return writer;
	}

	@Bean
	public Step step1(FlatFileItemWriter<ReceivedItems> writer) {
		return stepBuilderFactory.get("step1").<ReceivedItems, ReceivedItems>chunk(10).reader(reader())
				.processor(processor()).writer(writer).build();
	}

	@Bean
	public Job exportUserJob(Step step1) {
		return jobBuilderFactory.get("writerJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}

}
