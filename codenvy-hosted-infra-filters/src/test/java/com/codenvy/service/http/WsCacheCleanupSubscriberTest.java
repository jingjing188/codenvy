/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
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
package com.codenvy.service.http;

import com.codenvy.api.core.NotFoundException;
import com.codenvy.api.core.ServerException;
import com.codenvy.api.core.notification.EventService;
import com.codenvy.api.event.workspace.DeleteWorkspaceEvent;

import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.*;

import static org.mockito.Mockito.*;

@Listeners(MockitoTestNGListener.class)
public class WsCacheCleanupSubscriberTest {
    private static final String ID = "id";

    private static final String NAME = "name";

    private EventService eventService;

    @Mock
    private WorkspaceInfoCache.WorkspaceCacheLoader cacheLoader;

    private WorkspaceInfoCache workspaceInfoCache;

    private WsCacheCleanupSubscriber wsCacheCleanupSubscriber;

    @BeforeMethod
    public void setUp() throws Exception {
        eventService = new EventService();
        workspaceInfoCache = spy(new WorkspaceInfoCache(cacheLoader));
        wsCacheCleanupSubscriber = new WsCacheCleanupSubscriber(eventService, workspaceInfoCache);
    }

    @Test
    public void shouldRemoveItemsOnEvent() throws NotFoundException, ServerException {
        // given
        wsCacheCleanupSubscriber.subscribe();

        // when
        eventService.publish(new DeleteWorkspaceEvent(ID, true, NAME));

        //then
        verify(workspaceInfoCache, timeout(500)).removeByName(NAME);
        verify(workspaceInfoCache).removeById(ID);
    }
}
