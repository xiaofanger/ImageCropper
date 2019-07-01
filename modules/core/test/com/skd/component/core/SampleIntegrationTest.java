package com.skd.component.core;

import com.skd.component.SkdTestContainer;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import com.skd.component.core.baidu.api.service.OCRServiceBean;
import com.skd.component.core.baidu.api.service.baidu.api.OCRService;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleIntegrationTest {

    @ClassRule
    public static SkdTestContainer cont = SkdTestContainer.Common.INSTANCE;

    private Metadata metadata;
    private Persistence persistence;
    private DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        metadata = cont.metadata();
        persistence = cont.persistence();
        dataManager = AppBeans.get(DataManager.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoadUser() {
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            TypedQuery<User> query = em.createQuery(
                    "select u from sec$User u where u.login = :userLogin", User.class);
            query.setParameter("userLogin", "admin");
            List<User> users = query.getResultList();
            tx.commit();
            assertEquals(1, users.size());
        }
    }

    @Test
    public void testValidInfo() throws IOException {
        OCRService ocrService = AppBeans.get(OCRService.class);

        String absolutePath = new File(".").getAbsolutePath();
        File tPath = new File(absolutePath,"test/com/skd/component");

        String front = new File(tPath,"front.png").getCanonicalPath();
        String back = new File(tPath,"back.jpg").getCanonicalPath();
        String card = new File(tPath,"card.png").getCanonicalPath();

        //正面
        String fimg = ImageToBase64(front);
        ocrService.idCard(fimg,"front");

        //反面
        String bimg = ImageToBase64(back);
        ocrService.idCard(bimg,"back");

        //银行卡
        String bankCard = ImageToBase64(card);
        ocrService.bankCard(bankCard);
    }

    private String ImageToBase64(String imgPath) {
        InputStream is = null;
        byte[] data = null;
        try {
            is = new FileInputStream(imgPath);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = new String(Base64.encodeBase64(data));
        return s;
    }

}