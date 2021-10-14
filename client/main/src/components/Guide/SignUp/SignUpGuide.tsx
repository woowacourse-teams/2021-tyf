import {
  StyledContentContainer,
  StyledCreatorGuideContent,
  StyledTitle,
  StyledSubTitle,
  StyledContents,
  StyledImage,
  StyledContent,
} from './SignUpGuide.styles';

const SignUpGuide = () => {
  return (
    <StyledCreatorGuideContent>
      <StyledTitle>회원가입 방법</StyledTitle>
      <StyledContentContainer>
        <StyledSubTitle>1. 계좌 인증하기</StyledSubTitle>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/creator/account_1.png"
              alt="정산 관리 버튼 선택 이미지"
            />
          </StyledImage>
          <StyledContent>
            <p>1. 우측 메뉴바에서 [정산 관리] 를 선택하세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/creator/account_2.png"
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
              src="https://de56jrhz7aye2.cloudfront.net/guides/creator/account_3.png"
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
              src="https://de56jrhz7aye2.cloudfront.net/guides/creator/settlement_1.png"
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

export default SignUpGuide;
