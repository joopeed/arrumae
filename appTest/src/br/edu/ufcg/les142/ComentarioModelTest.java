package br.edu.ufcg.les142;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import br.edu.ufcg.les142.models.Comentario;
import com.parse.ParseUser;

/**
 * Created by lucasmc on 19/03/15.
 */
public class ComentarioModelTest extends ActivityInstrumentationTestCase2<InitialActivity> {

    public ComentarioModelTest() {
        super(InitialActivity.class);
    }

    private Comentario c;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        c = new Comentario();
    }

    @SmallTest
    public void testSetText() throws Exception {
        c.setText("teste");
        assertTrue(c.getText() != null);

    }

    @SmallTest
    public void testUser() throws Exception {
        ParseUser u = new ParseUser();
        u.setUsername("k");
        c.setUser(u);
        assertTrue(c.fetchIfNeeded().getParseUser("k") == null);
    }

    @SmallTest
    public void testQuery() throws Exception {

        assertTrue(c.getQuery() != null);
    }
}
