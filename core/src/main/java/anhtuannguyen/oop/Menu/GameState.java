package anhtuannguyen.oop.Menu;

/**
 * Enum đại diện cho các trạng thái chính của trò chơi.
 */
public enum GameState {
    /** Trạng thái hiển thị menu chính */
    MENU,

    /** Trạng thái chọn bản đồ chơi */
    SELECT_MAP,

    /** Trạng thái đang chơi trong game */
    IN_GAME,

    /** Trạng thái tạm dừng trò chơi */
    PAUSE,

    /** Trạng thái cài đặt trò chơi */
    SETTING,

    /** Trạng thái hiển thị kết quả sau khi chơi xong */
    RESULT
}