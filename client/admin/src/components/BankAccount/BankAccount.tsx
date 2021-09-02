import useBankAccount from '../../service/useBankAccount';
import {
  ItemContent,
  BankAccountList,
  BankAccountListItem,
  StyledBankAccount,
  Title,
  Bold,
  ButtonContainer,
  AgreeButton,
  DeclineButton,
  EmptyContent,
  BankImage,
} from './BankAccount.styles';

const BankAccount = () => {
  const { bankAccountList, agreeBankAccount, declineBankAccount } = useBankAccount();

  const showFullImage = (imgSrc: string) => {
    window.open(imgSrc, '_blank');
  };

  return (
    <StyledBankAccount>
      <Title>계좌신청목록</Title>
      <BankAccountList>
        {bankAccountList.length > 0 ? (
          bankAccountList.map((item, index) => (
            <BankAccountListItem key={index}>
              <ItemContent>
                <Bold>사용자ID</Bold> | {item.memberId}
                <br />
                <Bold>이메일</Bold> | {item.email}
                <br />
                <Bold>아이디</Bold> | {item.pageName}
                <br />
                <Bold>닉네임</Bold> | {item.nickname}
                <br />
                <Bold>계좌주</Bold> | {item.accountHolder}
                <br />
                <Bold>계좌번호</Bold> | {item.accountNumber}
                <br />
                <Bold>은행</Bold> | {item.bank}
                <br />
                <Bold>통장 이미지</Bold> |
                <BankImage
                  src={item.bankbookImageUrl}
                  alt={`${item.memberId}의 통장 사진`}
                  onClick={() => showFullImage(item.bankbookImageUrl)}
                />
                <br />
              </ItemContent>
              <ButtonContainer>
                <AgreeButton onClick={() => agreeBankAccount(item.memberId)}>수락</AgreeButton>
                <DeclineButton onClick={() => declineBankAccount(item.memberId)}>
                  거부
                </DeclineButton>
              </ButtonContainer>
            </BankAccountListItem>
          ))
        ) : (
          <EmptyContent>계좌신청목록이 없습니다.</EmptyContent>
        )}
      </BankAccountList>
    </StyledBankAccount>
  );
};

export default BankAccount;
