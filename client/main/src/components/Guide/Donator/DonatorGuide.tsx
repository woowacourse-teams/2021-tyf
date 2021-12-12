import { S3_URL } from '../../../constants/s3';
import {
  StyledContentContainer,
  StyledCreatorGuideContent,
  StyledTitle,
  StyledSubTitle,
  StyledContents,
  StyledImage,
  StyledContent,
} from './DonatorGuide.styles';

const DonatorGuide = () => {
  return (
    <StyledCreatorGuideContent>
      <StyledTitle>도네이션 하는 방법</StyledTitle>
      <StyledContentContainer>
        <StyledSubTitle>1. 포인트 충전하기</StyledSubTitle>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/charge_1.png`} alt="내 포인트 버튼 선택 " />
          </StyledImage>
          <StyledContent>
            <p>1. 우측 메뉴바에서 [내 포인트] 를 선택하세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/charge_2.png`} alt="내 포인트 페이지" />
          </StyledImage>
          <StyledContent>
            <p>2. [ 충전하기 ] 를 선택해주세요</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/charge_3.png`} alt="충전 금액 선택 페이지" />
          </StyledImage>
          <StyledContent>
            <p>3. 충전할 포인트를 선택해주세요.</p>
            <p>4. 약관에 동의해주세요.</p>
            <p>5. [ 결제하기 ] 를 선택해주세요.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/charge_4.png`} alt="정산 신청하기 이미지" />
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

      <StyledContentContainer>
        <StyledSubTitle>2. 도네이션하기</StyledSubTitle>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/donation_1.png`} alt="창작자 페이지" />
          </StyledImage>
          <StyledContent>
            <p>1. 창작제의 페이지에서 [ 도네이션하기 ] 를 선택합니다.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/donation_2.png`} alt="도네이션 페이지" />
          </StyledImage>
          <StyledContent>
            <p>2. 도네이션할 포인트를 입력 후, [ 도네이션 ] 을 선택합니다.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/donation_3.png`} alt="응원 메세지 페이지" />
          </StyledImage>
          <StyledContent>
            <p>3. 응원 메시지를 입력합니다.</p>
            <p>4. 메시지가 창작자에게만 보이도록 설정할 수 있습니다.</p>
          </StyledContent>
        </StyledContents>
        <StyledContents>
          <StyledImage>
            <img src={`${S3_URL}/guides/donator/donation_4.png`} alt="도네이션 완료 페이지" />
          </StyledImage>
          <StyledContent>
            <p>5. 도네이션이 완료되었습니다!</p>
          </StyledContent>
        </StyledContents>
      </StyledContentContainer>
    </StyledCreatorGuideContent>
  );
};

export default DonatorGuide;
