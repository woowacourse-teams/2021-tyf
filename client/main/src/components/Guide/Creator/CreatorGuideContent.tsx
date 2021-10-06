import {
  StyledContentContainer,
  StyledCreatorGuideContent,
  StyledTitle,
  StyledSubTitle,
  StyledContents,
  StyledImage,
  StyledContent,
} from './CreatorGuideContent.styles';

const CreatorGuideContent = () => {
  return (
    <StyledCreatorGuideContent>
      <StyledTitle>도네이션 받는 방법</StyledTitle>
      <StyledContentContainer>
        <StyledSubTitle>1. 계좌 인증하기</StyledSubTitle>
        <StyledContents>
          <StyledImage>
            <img
              src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0116ae90-a640-4f3b-92b2-bdf865df8413/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211006%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211006T081011Z&X-Amz-Expires=86400&X-Amz-Signature=1c1165a3d8439f8e98bd6d7ac63bf28874be65ef89126495abbc031df5f3b728&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
              alt="정산 관리 버튼 선택 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>1. 좌측 메뉴바에서 [정산 관리] 를 선택한다.</p>
          </StyledContent>
        </StyledContents>
      </StyledContentContainer>
      <StyledContentContainer>
        <StyledSubTitle>2. 정산 신청하기</StyledSubTitle>
      </StyledContentContainer>
    </StyledCreatorGuideContent>
  );
};

export default CreatorGuideContent;
