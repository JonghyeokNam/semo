import { formatDistanceToNow } from "date-fns";
import { ko } from "date-fns/locale";

// 이유진, 차현철
/**
 * 특정 날짜를 현재 시간 기준으로 상대 시간으로 변환합니다.
 * @param {Date|string|number} date - 변환할 날짜 (Date 객체, ISO 문자열, 타임스탬프).
 * @returns {string} 상대 시간 (예: "방금 전", "3시간 전").
 */

const formatRelativeTime = (date) => {
  if (!date) {
    throw new Error("date 파라미터는 필수입니다.");
  }

  return formatDistanceToNow(new Date(date), { addSuffix: true, locale: ko });
};

export default formatRelativeTime;
