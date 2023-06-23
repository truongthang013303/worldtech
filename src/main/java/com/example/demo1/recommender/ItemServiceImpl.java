package com.example.demo1.recommender;

import com.example.demo1.dto.NewDTO;
import com.example.demo1.service.impl.NewService;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.yarn.webapp.ResponseInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service
public class ItemServiceImpl implements ItemService{
    private DataSource dataSource;
    private static final double THRESHOLD=0.1;
    private DataModel dataModel;
    private static int recommendedItemsCount = 2;
    private NewService newService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    public ItemServiceImpl(DataSource dataSource, NewService newService) {
        this.newService=newService;
        this.dataSource = dataSource;
        this.dataModel = new MySQLJDBCDataModel(dataSource,
                "post_rating", "user_id", "post_id", "rating", null);
    }
    @Override
    public List<NewDTO> fillItemsItemBased(long userId) {
        List<NewDTO> items = new ArrayList<>();
        try {
            ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(this.dataModel);
            Recommender itemRecommender = new GenericItemBasedRecommender(this.dataModel,itemSimilarity);
            List<RecommendedItem> itemRecommendations = itemRecommender.recommend(userId, recommendedItemsCount);

            for (RecommendedItem item : itemRecommendations) {
                items.add(newService.findById(item.getItemID()));
            }
        } catch (Exception e)
        {
            items.clear();
            System.out.println(e);
            LOGGER.info(e.toString());
        }
        return items;
    }

    @Override
    public List<NewDTO> fillItemsUserBased(long userId) {
        List<NewDTO> items = new ArrayList<>();
        try {
            //UserSimilarity similarity = new PearsonCorrelationSimilarity(this.dataModel);
            UserSimilarity similarity = new EuclideanDistanceSimilarity(this.dataModel);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(THRESHOLD, similarity, this.dataModel);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(this.dataModel, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(userId, recommendedItemsCount);
            for (RecommendedItem item : recommendations) {
                items.add(newService.findById(item.getItemID()));
            }
        } catch (Exception e)
        {
            items.clear();
            System.out.println(e);
            LOGGER.info(e.toString());
        }
        return items;
    }
}
