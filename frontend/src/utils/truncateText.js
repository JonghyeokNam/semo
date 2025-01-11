/**
 * Truncates a string to a specified length and adds ellipsis if necessary.
 * @param {string} str - The string to truncate.
 * @param {number} n - The maximum length of the string.
 * @returns {string} - The truncated string.
 */

// 이유진
export const truncate = (str, n = 30) => {
    return str?.length > n ? str.substr(0, n - 1) + "..." : str;
  };
  