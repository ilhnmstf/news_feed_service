package news_feed_service.dto;

public record ApiError(String message, int status, String method, String path, String timestamp) {
}