package com.manjesh.couchbase.service;

import com.couchbase.client.java.Cluster;
import com.manjesh.couchbase.CouchbaseTemplate;
import com.manjesh.couchbase.config.CouchbaseClientConfig;
import com.manjesh.couchbase.document.Document;
import com.manjesh.couchbase.factory.CouchbaseClusterFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CouchbaseTemplateImplTest {
    private String bucketName = "events";
    private String bucketPassword = "events123";
    private CouchbaseTemplate couchbaseTemplate = null;

    @Before
    public void setUp() throws Exception {
        CouchbaseClientConfig clientConfig = new CouchbaseClientConfig();
        clientConfig.setNodes(Arrays.asList("localhost")); //"10.0.0.1", "10.0.0.2"
        clientConfig.setMutationTokensEnabled(true);
        clientConfig.setComputationPoolSize(5);
        Cluster cluster = new CouchbaseClusterFactory().cluster(clientConfig);
        couchbaseTemplate = new CouchbaseTemplateImpl(cluster, bucketName, bucketPassword);
    }

    @Test
    public void saveDocWithExpiryTest() {
        String baseEvent = "{  \"publicGuid\": \"1ae73514ea7a937bd681786eb6b5c999\",  \"pageId\": \"Mobile\",  \"placementId\": \"Timeline\",  \"convertEventCode\": \"9\",  \"prodTx\": \"GCCC\",  \"eventData\": \"cm11,cmSupp,cm13,publicGuid,custXrefId,countryCd,durDayCt,durSecondCt,convertEventCd,eventSubtyp,controlGroupCd,custTypeCd,pageId,placementId,offeringGrpId,offeringId,cmpgnId,offerId,offerContentId,prodGroupCd,treatmentGrpId,channelId,deviceOsNm,userAgentTx,prodTx,externalOfferCd,externalSourceCd,eapplySourceCd,treatmentRank,custLoggedInd,webOfferSourceCd,ptsID\"}";
        String docId = "1ae73514ea7a937bd681786eb6b5c999MobileTimeline906011235";
        Document document = new Document.Builder()
                .id(docId)
                .content(baseEvent)
                .expiry(60)
                .build();

        couchbaseTemplate.save(document);
    }

    @Test
    public void saveDocTest() {
        String baseEvent = "{  \"publicGuid\": \"1ae73514ea7a937bd681786eb6b5c99a\",  \"pageId\": \"Mobile\",  \"placementId\": \"Timeline\",  \"convertEventCode\": \"9\",  \"prodTx\": \"GCCC\",  \"eventData\": \"cm11,cmSupp,cm13,publicGuid,custXrefId,countryCd,durDayCt,durSecondCt,convertEventCd,eventSubtyp,controlGroupCd,custTypeCd,pageId,placementId,offeringGrpId,offeringId,cmpgnId,offerId,offerContentId,prodGroupCd,treatmentGrpId,channelId,deviceOsNm,userAgentTx,prodTx,externalOfferCd,externalSourceCd,eapplySourceCd,treatmentRank,custLoggedInd,webOfferSourceCd,ptsID\"}";
        String docId = "1ae73514ea7a937bd681786eb6b5c99aMobileTimeline906011235";
        Document document = new Document.Builder()
                .id(docId)
                .content(baseEvent)
                .build();

        couchbaseTemplate.save(document);
    }

    @Test
    public void saveOrUpdateDocTest() {
        String baseEvent = "{  \"publicGuid\": \"1ae73514ea7a937bd681786eb6b5c99b\",  \"pageId\": \"Mobile\",  \"placementId\": \"Timeline\",  \"convertEventCode\": \"9\",  \"prodTx\": \"GCCC\",  \"eventData\": \"cm11,cmSupp,cm13,publicGuid,custXrefId,countryCd,durDayCt,durSecondCt,convertEventCd,eventSubtyp,controlGroupCd,custTypeCd,pageId,placementId,offeringGrpId,offeringId,cmpgnId,offerId,offerContentId,prodGroupCd,treatmentGrpId,channelId,deviceOsNm,userAgentTx,prodTx,externalOfferCd,externalSourceCd,eapplySourceCd,treatmentRank,custLoggedInd,webOfferSourceCd,ptsID\"}";
        String docId = "1ae73514ea7a937bd681786eb6b5c99bMobileTimeline906011235";
        Document document = new Document.Builder()
                .id(docId)
                .content(baseEvent)
                .build();

        couchbaseTemplate.saveOrUpdate(document);

    }

    @Test
    public void retrieveDocumentTest() {
        String baseEvent = "{  \"publicGuid\": \"1ae73514ea7a937bd681786eb6b5c99a\",  \"pageId\": \"Mobile\",  \"placementId\": \"Timeline\",  \"convertEventCode\": \"9\",  \"prodTx\": \"GCCC\",  \"eventData\": \"cm11,cmSupp,cm13,publicGuid,custXrefId,countryCd,durDayCt,durSecondCt,convertEventCd,eventSubtyp,controlGroupCd,custTypeCd,pageId,placementId,offeringGrpId,offeringId,cmpgnId,offerId,offerContentId,prodGroupCd,treatmentGrpId,channelId,deviceOsNm,userAgentTx,prodTx,externalOfferCd,externalSourceCd,eapplySourceCd,treatmentRank,custLoggedInd,webOfferSourceCd,ptsID\"}";
        String docId = "1ae73514ea7a937bd681786eb6b5c99aMobileTimeline906011235";
        Document document = new Document.Builder()
                .id(docId)
                .content(baseEvent)
                .build();

        String retrieve = couchbaseTemplate.retrieve(document);
        System.out.println(retrieve);
    }

    @Test
    public void searchDocumentTest() {
        String query = "SELECT * FROM employee";
        List<String> documents = couchbaseTemplate.search(query);
        documents.stream().forEach(System.out::println);
    }

    @Test
    public void parameterizedSearchDocumentTest() {
        String query = "SELECT * FROM employee WHERE firstName=$1 or lastName=$2";
        String[] parameters = {"Alice", "Smith1"};
        List<String> documents = couchbaseTemplate.parameterizedSearch(query, parameters);
        documents.stream().forEach(System.out::println);
    }


}