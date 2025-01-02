import { useState, useEffect } from 'react';
import { API } from '../../utils/index'

const useFetchBoards = (page, size) => {
  const [boardData, setBoardData] = useState([]);
  const [totalItems, setTotalItems] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBoards = async (page = 1, size = 10) => {
      try {
        const response = await API.get(`/boards?page=${page}&size=${size}`);
        console.log(response.data.content);
        setBoardData(response.data.content); 
        setTotalItems(response.data.totalElements);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchBoards();
  }, [page, size]);

  return { boardData, totalItems, loading, error };
};

export default useFetchBoards;