import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.batch.item.excel.transform.RowSetTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Resource excelResource;

    @Autowired
    private EmployeeItemProcessor employeeItemProcessor;

    @Autowired
    private EmployeeItemWriter employeeItemWriter;

    @Autowired
    private EmployeeExcelItemReader employeeExcelItemReader;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                       @Value("classpath:/data/employee.xlsx") Resource excelResource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.excelResource = excelResource;
    }

    @Bean
    public Step excelFileToDatabaseStep() {
        return stepBuilderFactory
                .get("excelFileToDatabaseStep")
                .<RowSet, EmployeeDTO>chunk(10)
                .reader(employeeExcelItemReader)
                .processor(employeeItemProcessor)
                .writer(employeeItemWriter)
                .build();
    }

    @Bean
    public Job excelFileToDatabaseJob() {
        return jobBuilderFactory
                .get("excelFileToDatabaseJob")
                .start(excelFileToDatabaseStep())
                .build();
    }
}
