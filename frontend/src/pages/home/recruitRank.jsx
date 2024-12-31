import React, { useEffect } from "react";
import * as S from "./style";
import { useRecruitRankStore } from '../../store/useRankStore';

const RecruitRank = () => {
  const { ranks, fetchRanks, loading, error } = useRecruitRankStore();

  useEffect(() => {
    fetchRanks(); // 데이터 가져오기
  }, [fetchRanks]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

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
          {ranks.map((item, index) => (
            <tr key={index}>
              {/* 순위를 정적으로 매핑 (인덱스 + 1) */}
              <S.TableCell $rank={index + 1}>{index + 1}</S.TableCell>
              <S.TableCell $rank={index + 1}>{item.campusName} 캠퍼스</S.TableCell>
              <S.TableCell $rank={index + 1}>{item.score}</S.TableCell>
            </tr>
          ))}
        </tbody>
      </S.RankTable>
    </S.RankContainer>
  );
};

export default RecruitRank;
