package com.asapp.MessageSvc;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.asapp.content.Content;
import com.asapp.content.Image;
import com.asapp.content.Text;
import com.asapp.content.Video;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class MessageDAO {

    private final JdbcTemplate jdbcTemplate;
    KeyHolder keyHolder;

    @Autowired
    public MessageDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate =  jdbcTemplate;
        keyHolder = new GeneratedKeyHolder();
    }
    public Optional<MessageResponse> sendMessage(SendMessageRequest sendMessageRequest) {
        MessageResponse messageResponse;
        int contentId = addContent(sendMessageRequest.getContent());
        if (contentId == -1 ){
            return Optional.empty();
        }

        String insertSql =
            "INSERT INTO message (" +
                " sender, " +
                " recipient, " +
                " sent_at, " +
                " content_id, " +
                " status )" +
                "VALUES (?, ?, ?, ?, ?)";

        Instant sentAt = new Timestamp(System.currentTimeMillis()).toInstant();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql);
                ps.setInt(1, Integer.parseInt(sendMessageRequest.getSender()));
                ps.setInt(2, Integer.parseInt(sendMessageRequest.getRecipient()));
                ps.setString(3, sentAt.toString());
                ps.setInt(4, contentId);
                ps.setString(5, "Sent");
                return ps;
            }, keyHolder);
            messageResponse = new MessageResponse((int) keyHolder.getKey(),sentAt);
            return  Optional.of(messageResponse);
        } catch (Exception e) {
            messageResponse = new MessageResponse();
            messageResponse.setErrorResponse(ErrorResponse.UNABLE_TO_SEND_MESSAGE);
            return Optional.of(messageResponse);
        }
    }


    private int addContent(Content content) {
        String type = content.getType();
        int id;
        String insertSql;
        if (type.equals("text") || type.equals("string")) {
            id = addText(content.getText());
            insertSql = "INSERT INTO content (type, text_id) VALUES (?, ?)";
        } else if (type.equals("image")) {
            id = addImage(content.getImage());
            insertSql = "INSERT INTO content (type, image_id) VALUES (?, ?)";

        } else if (type.equals("video")){
            id = addVideo(content.getVideo());
            insertSql = "INSERT INTO contetn (type, video_id) VALUES (?, ?)";
        } else {
            id = -1;
            insertSql = "";
        }
        if (id!=-1) {
            try {
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertSql);
                    ps.setString(1, type);
                    ps.setInt(2, id);
                    return ps;
                }, keyHolder);
                return (int) keyHolder.getKey();
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    private int addVideo(Video video) {
        if(video == null) {
            return -1;
        }
        String insertSql = "INSERT INTO image (url, source) VALUES (?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql);
                ps.setString(1, video.getUrl());
                ps.setString(2, video.getSource());
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKey();
        } catch (Exception e) {
            return -1;
        }
    }

    private int addImage(Image image) {
        if(image == null) {
            return -1;
        }
        String insertSql = "INSERT INTO image (url, height, width) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql);
                ps.setString(1, image.getUrl());
                ps.setDouble(2, image.getHeight());
                ps.setDouble(3, image.getWidth());
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKey();
        } catch (Exception e) {
            return -1;
        }

    }

    private int addText(Text text) {

        if(text == null) {
            return -1;
        }
        String insertSql = "INSERT INTO text (text) VALUES (?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql);
                ps.setString(1, text.getText());
                return ps;
            }, keyHolder);
            return (int) keyHolder.getKey();
        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<SendMessageResponse> getMessage(GetMessageRequest getMessageRequest) {
        int start = getMessageRequest.getStart();
        int limit = getMessageRequest.getLimit();
        int recipient = Integer.parseInt(getMessageRequest.getRecipient());
        List<Map<String, Object>> objList;
        ArrayList<SendMessageResponse> messageList = new ArrayList<>();
        String selectSql = "Select * from message where message_id > ? AND recipient = ? LIMIT ?";
        try{
            objList = this.jdbcTemplate.queryForList(selectSql, start,recipient, limit);
            for (Map<String, Object> row : objList) {
                SendMessageResponse sendMessageResponse = new SendMessageResponse();
                sendMessageResponse.setId((Integer) row.get("message_id"));
                sendMessageResponse.setSender((Integer) row.get("sender"));
                sendMessageResponse.setRecipient((Integer) row.get("recipient"));
                sendMessageResponse.setTimestamp((String) row.get("sent_at"));
                Content content = getContent ((Integer) row.get("content_id"));
                sendMessageResponse.setContent(content);

                messageList.add(sendMessageResponse);
            }
            return messageList;
        } catch (Exception e){
            new ArrayList<SendMessageResponse>();
        }

        return null;
    }

    private Content getContent(int contentId) {
        String selectSql = "Select * from content where content_id = ?";
        System.out.println(contentId);
        try {
            Content content = jdbcTemplate.queryForObject(selectSql, new Object[] {contentId}, (rs, rowNum) ->
                new Content(rs.getInt("content_id"),
                    rs.getString("type"),
                    new Text(rs.getInt("text_id")),
                    new Image(rs.getInt("image_id")),
                    new Video(rs.getInt("video_id"))

                ));
            String type = content.getType();
            if (type.equals("text") || type.equals("string")) {
                content.setText(getText(content.getText().getTextId()));
                content.setImage(null);
                content.setVideo(null);
            } else if (type.equals("image")) {
                content.setImage(getImage(content.getImage().getImageId()));
                content.setText(null);
                content.setVideo(null);
            } else if (type.equals("video")) {
                content.setVideo(getVideo(content.getVideo().getVideoId()));
                content.setImage(null);
                content.setText(null);
            } else {
                return new Content(contentId);
            }
            return content;
        } catch (Exception e) {
            return new Content(contentId);
        }

    }

    private Video getVideo(int videoId) {
        String sql = "select * from video where vidoe_id = ?";
        Video video = jdbcTemplate.queryForObject(sql, new Object[] {videoId}, (rs, rowNum) ->
            new Video(
                rs.getInt("vidoe_id"),
                rs.getString("url"),
                rs.getString("source")
            ));

        return video;
    }

    private Image getImage(int imageId) {
        String sql = "select * from image where image_id = ?";
        Image image = jdbcTemplate.queryForObject(sql, new Object[]{imageId}, (rs, rowNum) ->
            new Image(
                rs.getInt("image_id"),
                rs.getString("url"),
                rs.getDouble("height"),
                rs.getDouble("width")
            ));

        return  image;
    }

    private Text getText(int textId) {
        String sql = "select * from text where text_id = ?";
        Text text = jdbcTemplate.queryForObject(sql, new Object[]{textId}, (rs, rowNum) ->
            new Text(
                rs.getInt("text_id"),
                rs.getString("text")
            ));

        return text;
    }
}
