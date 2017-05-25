package mx.infotec.dads.greenorion.web.rest;

import mx.infotec.dads.greenorion.GreenorionApp;

import mx.infotec.dads.greenorion.domain.Subscription;
import mx.infotec.dads.greenorion.repository.SubscriptionRepository;
import mx.infotec.dads.greenorion.service.SubscriptionService;
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
 * Test class for the SubscriptionResource REST controller.
 *
 * @see SubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreenorionApp.class)
public class SubscriptionResourceIntTest {

    private static final String DEFAULT_ENTITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_ORION_URL = "AAAAAAAAAA";
    private static final String UPDATED_ORION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RETURNED_ATTR = "AAAAAAAAAA";
    private static final String UPDATED_RETURNED_ATTR = "BBBBBBBBBB";

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSubscriptionMockMvc;

    private Subscription subscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriptionResource subscriptionResource = new SubscriptionResource(subscriptionService);
        this.restSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(subscriptionResource)
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
    public static Subscription createEntity() {
        Subscription subscription = new Subscription()
            .entityId(DEFAULT_ENTITY_ID)
            .entityType(DEFAULT_ENTITY_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .attrCondition(DEFAULT_ATTR_CONDITION)
            .orionUrl(DEFAULT_ORION_URL)
            .returnedAttr(DEFAULT_RETURNED_ATTR);
        return subscription;
    }

    @Before
    public void initTest() {
        subscriptionRepository.deleteAll();
        subscription = createEntity();
    }

    @Test
    public void createSubscription() throws Exception {
        int databaseSizeBeforeCreate = subscriptionRepository.findAll().size();

        // Create the Subscription
        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isCreated());

        // Validate the Subscription in the database
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Subscription testSubscription = subscriptionList.get(subscriptionList.size() - 1);
        assertThat(testSubscription.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testSubscription.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testSubscription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscription.getAttrCondition()).isEqualTo(DEFAULT_ATTR_CONDITION);
        assertThat(testSubscription.getOrionUrl()).isEqualTo(DEFAULT_ORION_URL);
        assertThat(testSubscription.getReturnedAttr()).isEqualTo(DEFAULT_RETURNED_ATTR);
    }

    @Test
    public void createSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionRepository.findAll().size();

        // Create the Subscription with an existing ID
        subscription.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setEntityId(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setEntityType(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setDescription(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAttrConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setAttrCondition(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOrionUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setOrionUrl(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkReturnedAttrIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionRepository.findAll().size();
        // set the field null
        subscription.setReturnedAttr(null);

        // Create the Subscription, which fails.

        restSubscriptionMockMvc.perform(post("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isBadRequest());

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSubscriptions() throws Exception {
        // Initialize the database
        subscriptionRepository.save(subscription);

        // Get all the subscriptionList
        restSubscriptionMockMvc.perform(get("/api/subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscription.getId())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.toString())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].attrCondition").value(hasItem(DEFAULT_ATTR_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].orionUrl").value(hasItem(DEFAULT_ORION_URL.toString())))
            .andExpect(jsonPath("$.[*].returnedAttr").value(hasItem(DEFAULT_RETURNED_ATTR.toString())));
    }

    @Test
    public void getSubscription() throws Exception {
        // Initialize the database
        subscriptionRepository.save(subscription);

        // Get the subscription
        restSubscriptionMockMvc.perform(get("/api/subscriptions/{id}", subscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscription.getId()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.toString()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.attrCondition").value(DEFAULT_ATTR_CONDITION.toString()))
            .andExpect(jsonPath("$.orionUrl").value(DEFAULT_ORION_URL.toString()))
            .andExpect(jsonPath("$.returnedAttr").value(DEFAULT_RETURNED_ATTR.toString()));
    }

    @Test
    public void getNonExistingSubscription() throws Exception {
        // Get the subscription
        restSubscriptionMockMvc.perform(get("/api/subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubscription() throws Exception {
        // Initialize the database
        subscriptionService.save(subscription);

        int databaseSizeBeforeUpdate = subscriptionRepository.findAll().size();

        // Update the subscription
        Subscription updatedSubscription = subscriptionRepository.findOne(subscription.getId());
        updatedSubscription
            .entityId(UPDATED_ENTITY_ID)
            .entityType(UPDATED_ENTITY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .attrCondition(UPDATED_ATTR_CONDITION)
            .orionUrl(UPDATED_ORION_URL)
            .returnedAttr(UPDATED_RETURNED_ATTR);

        restSubscriptionMockMvc.perform(put("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscription)))
            .andExpect(status().isOk());

        // Validate the Subscription in the database
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeUpdate);
        Subscription testSubscription = subscriptionList.get(subscriptionList.size() - 1);
        assertThat(testSubscription.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testSubscription.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testSubscription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscription.getAttrCondition()).isEqualTo(UPDATED_ATTR_CONDITION);
        assertThat(testSubscription.getOrionUrl()).isEqualTo(UPDATED_ORION_URL);
        assertThat(testSubscription.getReturnedAttr()).isEqualTo(UPDATED_RETURNED_ATTR);
    }

    @Test
    public void updateNonExistingSubscription() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionRepository.findAll().size();

        // Create the Subscription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubscriptionMockMvc.perform(put("/api/subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscription)))
            .andExpect(status().isCreated());

        // Validate the Subscription in the database
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteSubscription() throws Exception {
        // Initialize the database
        subscriptionService.save(subscription);

        int databaseSizeBeforeDelete = subscriptionRepository.findAll().size();

        // Get the subscription
        restSubscriptionMockMvc.perform(delete("/api/subscriptions/{id}", subscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        assertThat(subscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscription.class);
    }
}
