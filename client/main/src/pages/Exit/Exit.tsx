import styled from 'styled-components';
import Logo from '../../components/@molecule/Logo/Logo';

export default function Exit() {
  return (
    <Wrapper>
      <h1>땡큐포 사이트 종료 안내</h1>
      <div className="contents">
        <div>
          <p>안녕하세요. 팀 TYF 입니다.</p>
          <p>먼저 땡큐포 서비스를 아껴주신분들께 진심으로 감사의 말씀 전합니다.</p>
        </div>
        <div>
          <p>
            아쉽게도 <strong>땡큐포 서비스가 종료되었음을 안내드립니다.</strong>
          </p>
          <p>서비스를 지속하지 못하게 되어 깊이 사과의 말씀 드립니다.</p>
        </div>
        <div>
          <p>
            더이상의 기술 지원 및 서비스 유지가 어려워져 서비스 종료의 결정을 내리게 되었습니다.
          </p>
          <p>그동안 땡큐포 서비스를 이용해주신 여러분께 진심으로 감사드립니다.</p>
        </div>
        <div>
          <p>감사합니다.</p>
        </div>
      </div>
      <div className="more">
        <p>
          추가적인 서비스와 관련된 문의는 <strong>thank.you.for.tyf@gmail.com</strong> 메일로
          부탁드립니다.
        </p>
      </div>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  line-height: 1.5rem;

  h1 {
    font-size: 32px;
    font-weight: bold;
    margin-bottom: 40px;
  }

  strong {
    font-weight: bold;
  }

  .contents {
    display: flex;
    flex-direction: column;
    justify-contents: center;
    align-items: center;
    text-align: center;

    & > div {
      margin-bottom: 16px;
    }
  }
`;
