import { create } from "zustand";
import { API } from "../lib/apis/utils";

export const useChatStore = create((set, get) => ({
  chatRooms: [],

  // 채팅방 목록 불러오기
  fetchChatRooms: async () => {
    try {
      const response = await API.get("/chatrooms/");
      const { resultCode, result } = response.data;
      if (resultCode === "GET_CHATROOMLIST_SUCCESS") {
        // 백엔드가 ChatRoomDto[] 배열을 돌려줌
        set({ chatRooms: result });
      } else {
        console.error("Failed to load chat rooms");
      }
    } catch (error) {
      console.error("fetchChatRooms error:", error);
    }
  },

  // 특정 방의 마지막 메시지, 시간, 안 읽은 개수 등을 부분 업데이트
  updateRoomData: (roomId, lastMessage, lastMessageTime, unreadCount) => {
    set((state) => {
      const updated = state.chatRooms.map((room) => {
        if (room.roomId === roomId) {
          return {
            ...room,
            lastMessage: lastMessage !== undefined ? lastMessage : room.lastMessage,
            lastMessageTime: lastMessageTime !== undefined ? lastMessageTime : room.lastMessageTime,
            unreadCount: unreadCount !== undefined ? unreadCount : room.unreadCount,
          };
        }
        return room;
      });
      return { chatRooms: updated };
    });
  },
}));
