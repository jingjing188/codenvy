/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2015] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.api.account.billing;

import com.codenvy.api.account.PaymentService;
import com.codenvy.api.account.promotion.PromotionService;
import com.codenvy.api.dao.billing.BraintreeCreditCardDaoImpl;
import com.codenvy.api.dao.sql.SqlBonusDao;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import static org.eclipse.che.inject.Matchers.names;

/**
 * @author Sergii Kabashniuk
 */
public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(com.braintreegateway.BraintreeGateway.class).to(GuiceBraintreeGateway.class).asEagerSingleton();
        bind(PaymentService.class).to(BraintreePaymentService.class).asEagerSingleton();
        bind(BillingPeriod.class).to(MonthlyBillingPeriod.class);
        bind(BonusDao.class).to(SqlBonusDao.class);
        bind(com.codenvy.api.account.billing.CreditCardDao.class).to(BraintreeCreditCardDaoImpl.class);
        bind(TemplateProcessor.class);

        bind(CreditCardService.class);
        bind(InvoiceService.class);
        bind(BillingRestService.class);
        bind(PromotionService.class);

        CreateBonusInterceptor createBonusInterceptor = new CreateBonusInterceptor();
        requestInjection(createBonusInterceptor);
        bindInterceptor(Matchers.subclassesOf(BonusDao.class),
                        names("create"),
                        createBonusInterceptor);
    }
}