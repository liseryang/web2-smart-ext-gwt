package masterjava.web2.webapp.controller;

import masterjava.web2.common.dao.GenericDao;
import masterjava.web2.common.spring.XmlDataEditor;
import masterjava.web2.model.SimpleTableEntity;
import masterjava.web2.util.ConvertUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * User: GKislin
 * Date: 13.12.2010
 */
@Controller
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
public class TableController {

    private static final Logger LOGGER = Logger.getLogger(TableController.class);
    private GenericDao<SimpleTableEntity, Long> simpleTableDao;

    public TableController() {
        LOGGER.info("\n\n\n--------- START APP ----------");
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new XmlDataEditor());
    }

    @Autowired
    @Required
    public void setSimpleTableDao(GenericDao<SimpleTableEntity, Long> simpleTableDao) {
        this.simpleTableDao = simpleTableDao;
    }

    @RequestMapping("/fetch.do")
    public ModelAndView handleFetch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SimpleTableEntity> list = simpleTableDao.loadAll();
        LOGGER.info("fetch table");

        response.getWriter().write(ConvertUtil.toJson(list));
        response.getWriter().close();
        return null;
    }

//    @RequestMapping("/update.do")
//    public ModelAndView handleUpdate(
//            @RequestParam("id") long id,
//            @RequestParam("ttext") String ttext,
//            @RequestParam("tint") int tint,
//            @RequestParam("tdate") Date tdate) throws Exception {
//        SimpleTableEntity entity = simpleTableDao.get(id);
//        entity.setTtext(ttext);
//        entity.setTint(tint);
//        entity.setTdate(new Timestamp(tdate.getTime()));
//        simpleTableDao.save(entity);
//        LOGGER.info("update table: " + entity.toJson());
//
//        ModelAndView mv = new ModelAndView("update");
//        mv.addObject("data", entity);
//        return null;
//    }
//
//    @RequestMapping("/add.do")
//    public ModelAndView handleAdd(
//            @RequestParam("ttext") String ttext,
//            @RequestParam("tint") int tint,
//            @RequestParam("tdate") Date tdate) throws Exception {
//
//        SimpleTableEntity entity = new SimpleTableEntity(ttext, tint, tdate);
//        entity.setId(simpleTableDao.save(entity));
//        LOGGER.info("add to table: " + entity.toJson());
//
//        ModelAndView mv = new ModelAndView("update");
//        mv.addObject("data", entity);
//        return null;
//    }
//
//    @RequestMapping("/remove.do")
//    public ModelAndView handleRemove(@RequestParam("id") long id) throws Exception {
//        simpleTableDao.delete(id);
//        LOGGER.info("remove from table: " + id);
//
//        ModelAndView mv = new ModelAndView("remove");
//        mv.addObject("data", id);
//        return null;
//    }
}
