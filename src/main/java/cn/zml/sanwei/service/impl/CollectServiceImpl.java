package cn.zml.sanwei.service.impl;

import cn.zml.sanwei.config.SanweiException;
import cn.zml.sanwei.dao.BookDao;
import cn.zml.sanwei.dao.CollectDao;
import cn.zml.sanwei.model.Book;
import cn.zml.sanwei.model.CollectBook;
import cn.zml.sanwei.service.CollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.zml.sanwei.config.Constant.DATA_ERROR_NO_SUCH_BOOK;
import static cn.zml.sanwei.config.Constant.HAS_COLLECTED_BOOK;

/**
 * 服务实现层
 * @author fanyidong
 * @date Created in 2019-03-17
 */
@Service
public class CollectServiceImpl implements CollectService {

    private static Logger log = LoggerFactory.getLogger(CollectServiceImpl.class);

    @Autowired
    private CollectDao collectDao;
    @Autowired
    private BookDao bookDao;

    @Override
    public void insert(String userId, String bookId) throws SanweiException {
        Book book = bookDao.getBookById(bookId);
        if (book==null) {
            throw new SanweiException(DATA_ERROR_NO_SUCH_BOOK);
        }
        if (collectDao.hasBookCollected(userId, bookId) != 0) {
            throw new SanweiException(HAS_COLLECTED_BOOK);
        }
        collectDao.insert(userId, bookId);
        log.info("用户【" + userId + "】收藏书籍【" + book.getName() + "】成功");
    }

    @Override
    public void delete(String userId, String bookId) {
        collectDao.delete(userId, bookId);
    }

    @Override
    public List<CollectBook> getCollectionsFromBookAndCollectByUserId(String userId) {
        return collectDao.getCollectionsFromBookAndCollectByUserId(userId);
    }
}
