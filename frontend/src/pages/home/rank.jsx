import React, { useEffect, useState } from "react";
import * as S from "./style";

const Rank = ({ url }) => {
  const [campusRankings, setCampusRankings] = useState([
    { rank: 1, campus: "동대문 캠퍼스", score: 120 },
    { rank: 2, campus: "강동 캠퍼스", score: 100 },
    { rank: 3, campus: "동작 캠퍼스", score: 90 },
    { rank: 3, campus: "광진 캠퍼스", score: 90 },
    { rank: 5, campus: "성동 캠퍼스", score: 70 }]);

  useEffect(() => {
    const fetchCampusRankings = async () => {
      try {
        const response = await fetch(url); 
        const data = await response.json();
        setCampusRankings(data); 
      } catch (error) {
        console.error("API 요청 오류:", error);
      }
    };

    fetchCampusRankings();
  }, [url]); // url이 변경될 때마다 데이터를 다시 요청

  return (
    <S.RankContainer>
      <S.RankTable>
        <thead>
          <tr>
            <S.TableHeader>순위</S.TableHeader>
            <S.TableHeader>캠퍼스</S.TableHeader>
            <S.TableHeader>점수</S.TableHeader>
          </tr>
        </thead>
        <tbody>
          {campusRankings.map((item, index) => (
            <tr key={index}>
              <S.TableCell rank={item.rank}>{item.rank}</S.TableCell>
              <S.TableCell rank={item.rank}>{item.campus}</S.TableCell>
              <S.TableCell rank={item.rank}>{item.score}</S.TableCell>
            </tr>
          ))}
        </tbody>
      </S.RankTable>
    </S.RankContainer>
  );
};

export default Rank;
