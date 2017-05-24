package mx.infotec.dads.greenorion.web.rest;

import mx.infotec.dads.greenorion.GreenorionApp;

import mx.infotec.dads.greenorion.domain.Notification;
import mx.infotec.dads.greenorion.repository.NotificationRepository;
import mx.infotec.dads.greenorion.service.NotificationService;
import mx.infotec.dads.greenorion.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NotificationResource REST controller.
 *
 * @see NotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreenorionApp.class)
public class NotificationResourceIntTest {

    private static final String DEFAULT_ORION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final Integer DEFAULT_O_3 = 1;
    private static final Integer UPDATED_O_3 = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restNotificationMockMvc;

    private Notification notification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NotificationResource notificationResource = new NotificationResource(notificationService);
        this.restNotificationMockMvc = MockMvcBuilders.standaloneSetup(notificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity() {
        Notification notification = new Notification()
            .orionId(DEFAULT_ORION_ID)
            .type(DEFAULT_TYPE)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .o3(DEFAULT_O_3)
            .name(DEFAULT_NAME);
        return notification;
    }

    @Before
    public void initTest() {
        notificationRepository.deleteAll();
        notification = createEntity();
    }

    @Test
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getOrionId()).isEqualTo(DEFAULT_ORION_ID);
        assertThat(testNotification.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotification.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testNotification.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testNotification.geto3()).isEqualTo(DEFAULT_O_3);
        assertThat(testNotification.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checko3IsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.seto3(null);

        // Create the Notification, which fails.

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.save(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId())))
            .andExpect(jsonPath("$.[*].orionId").value(hasItem(DEFAULT_ORION_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.toString())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.toString())))
            .andExpect(jsonPath("$.[*].o3").value(hasItem(DEFAULT_O_3)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.save(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId()))
            .andExpect(jsonPath("$.orionId").value(DEFAULT_ORION_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.toString()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.toString()))
            .andExpect(jsonPath("$.o3").value(DEFAULT_O_3))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationService.save(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findOne(notification.getId());
        updatedNotification
            .orionId(UPDATED_ORION_ID)
            .type(UPDATED_TYPE)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .o3(UPDATED_O_3)
            .name(UPDATED_NAME);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotification)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getOrionId()).isEqualTo(UPDATED_ORION_ID);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotification.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testNotification.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testNotification.geto3()).isEqualTo(UPDATED_O_3);
        assertThat(testNotification.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationService.save(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Get the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
    }
}
