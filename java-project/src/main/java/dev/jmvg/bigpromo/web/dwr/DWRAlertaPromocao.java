package dev.jmvg.bigpromo.web.dwr;

import dev.jmvg.bigpromo.web.repository.PromocaoRepository;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
@RemoteProxy
public class DWRAlertaPromocao {

    private PromocaoRepository repository;

    @Autowired
    public DWRAlertaPromocao(PromocaoRepository repository) {
        this.repository = repository;
    }

    private Timer timer;

    @RemoteMethod
    public synchronized void init(){
        System.out.println("DWR está ativado");
        LocalDateTime lastDate = getDataCadastroByUltimaPromocao();

        WebContext context = WebContextFactory.get();

        timer = new Timer();
        timer.schedule(new AlertTask(context, lastDate), 10000, 60000);
    }

    private LocalDateTime getDataCadastroByUltimaPromocao(){
        PageRequest pageRequest = PageRequest.of(0,1, Sort.Direction.DESC, "dataCadastro" );
        return repository.findUltimaDataDePromocao(pageRequest).getContent().get(0);
    }

    private class AlertTask extends TimerTask {

        private LocalDateTime lastDate;
        private WebContext context;
        private long count;

        public AlertTask(WebContext context, LocalDateTime lastDate) {
            super();
            this.lastDate = lastDate;
            this.context = context;
        }

        @Override
        public void run() {
            String session = context.getScriptSession().getId();

            Browser.withSession(context, session, new Runnable() {
                @Override
                public void run() {
                    Map<String, Object> map = repository.totalAndUltimaPromocaoByDataCadastro(lastDate);
                    count = (Long) map.get("count");
                    lastDate = map.get("lastDate") == null ? lastDate : (LocalDateTime) map.get("lastDate");

                    Calendar time = Calendar.getInstance();
                    time.setTimeInMillis(context.getScriptSession().getLastAccessedTime());
                    System.out.println("Count: " + count + ", LastDate: "+ lastDate + ", < "+ session + " > "+ "< "+ time.getTime() +" >");

                    if(count > 0){
                        ScriptSessions.addFunctionCall("showButton", count);
                    }
                }
            });



        }
    }
}
