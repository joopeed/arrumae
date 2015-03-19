package br.edu.ufcg.les142;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import br.edu.ufcg.les142.models.Comentario;
import br.edu.ufcg.les142.models.Relato;
import br.edu.ufcg.les142.models.StatusRelato;
import br.edu.ufcg.les142.models.TipoRelato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucasmc on 19/03/15.
 */
public class RelatoModelTest extends ActivityInstrumentationTestCase2<InitialActivity> {
    private Relato r;
    public RelatoModelTest() {
        super(InitialActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        r = new Relato();
    }

    @SmallTest
    public void testShouldHaveDesc(){
        r.setDescricao("desc");
        assertTrue(r.getDescricao() != null);
    }

    @SmallTest
    public void testShouldHaveStatus(){
        StatusRelato s = StatusRelato.ABERTO;
        r.setStatusRelato(s);
        assertTrue(r.getStatusRelato() != null);
    }

    @SmallTest
    public void testShouldHaveTipo(){
        TipoRelato s = TipoRelato.ESTRADA;
        r.setTipoRelato(s);
        assertTrue(r.getTipoRelato() != null);
    }

    @SmallTest
    public void testShouldHaveComentarios(){
        List<Comentario> s = new ArrayList<Comentario>();
        r.setComentarios(s);
        assertTrue(r.getComentarios() != null);
    }
}
