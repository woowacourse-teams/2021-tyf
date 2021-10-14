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
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/sign-up/sign-up-1.png"
              alt="회원가입 버튼"
            />
          </StyledImage>
          <StyledContent>
            <p>1. 우측 상단의 [회원가입]을 선택하세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/sign-up/sign-up-2.png"
              alt="회원가입에 이용할 서비스 목록"
            />
          </StyledImage>
          <StyledContent>
            <p>2. 회원가입에 이용할 서비스를 선택합니다.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/sign-up/sign-up-3.png"
              alt="약관 목록"
            />
          </StyledImage>
          <StyledContent>
            <p>
              3. 약관을 확인한 후 동의를 해주신 다음,
              <br />
              &nbsp;&nbsp;[ 계속하기 ]를 눌러주세요.
            </p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/sign-up/sign-up-4.png"
              alt="내 페이지 주소 입력"
            />
          </StyledImage>
          <StyledContent>
            <p>4. 도네이션 받을 주소를 적어주세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/guides/sign-up/sign-up-5.png"
              alt="내 닉네임 입력"
            />
          </StyledImage>
          <StyledContent>
            <p>5. 사용할 닉네임을 입력해주세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img
              src="https://de56jrhz7aye2.cloudfront.net/guides/guides/sign-up/sign-up-6.png"
              alt="회원가입 완료"
            />
          </StyledImage>
          <StyledContent>
            <p>6. 회원가입이 완료되었습니다!</p>
          </StyledContent>
        </StyledContents>
      </StyledContentContainer>
    </StyledCreatorGuideContent>
  );
};

export default SignUpGuide;
