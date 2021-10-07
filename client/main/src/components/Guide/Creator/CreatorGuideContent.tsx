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
              src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0116ae90-a640-4f3b-92b2-bdf865df8413/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211007%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211007T081151Z&X-Amz-Expires=86400&X-Amz-Signature=153835e16ceee3f561b818cf36d1452a5924754c575e15bc9b1a5f6b848a3356&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
              alt="정산 관리 버튼 선택 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>1. 좌측 메뉴바에서 [정산 관리] 를 선택하세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cb23dc99-e21e-4d55-b71e-a974f007be1c/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211007%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211007T072752Z&X-Amz-Expires=86400&X-Amz-Signature=b547902782700335a1c4f79976c071f6cb52babeff25ee4afe9f0e0e86d3a1e0&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
              alt="정산 계좌 등록 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>2. 본인 명의 계좌 정보를 입력해주세요.</p>
            <p>3. 작성한 계좌 정보를 제출하세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/20eed75c-f728-4787-babd-3b4ff630c354/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211007%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211007T073005Z&X-Amz-Expires=86400&X-Amz-Signature=798e0afef2f0aa422f8eb40e7ec6a0438265aa3cca02d71ddc2901c8fd208cb2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
              alt="등록 요청 확인 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>
              4. 제출된 요청이 승인될때까지 기다려주세요.
              <br />
              &nbsp;&nbsp;[정산 관리] 페이지에서 승인 여부를 확인할 수 있습니다.
            </p>
          </StyledContent>
        </StyledContents>
      </StyledContentContainer>

      <StyledContentContainer>
        <StyledSubTitle>2. 정산 신청하기</StyledSubTitle>
        <StyledContents>
          <StyledImage>
            <img
              src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b48078bf-87d7-4ef5-954c-cf8a8387a4ea/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20211007%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20211007T080846Z&X-Amz-Expires=86400&X-Amz-Signature=2012c3d11b8be099efba14541eb4d527542be74fc9413e9fdacd3e0c987abc24&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
              alt="정산 신청하기 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>
              1. 계좌 등록 승인 이후 [정산 관리] 페이지에서 [정산 신청하기] 를 요청하세요. <br />
              &nbsp;&nbsp;정산 받을 수 있는 금액이 10000원 이상이여야만 합니다.
            </p>

            <p>2. 제출된 요청이 승인될때까지 기다려주세요.</p>
          </StyledContent>
        </StyledContents>
      </StyledContentContainer>
    </StyledCreatorGuideContent>
  );
};

export default CreatorGuideContent;
