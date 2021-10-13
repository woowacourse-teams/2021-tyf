import { useState } from 'react';
import useUserInfo from '../../../service/user/useUserInfo';
import Button from '../../@atom/Button/Button.styles';
import Input from '../../@atom/Input/Input.styles';
import Textarea from '../../@atom/Textarea/Textarea';
import {
  InputTitle,
  InputWrapper,
  StyledContactContent,
  StyledTitle,
} from './ContactContent.styles';

const ContactContent = () => {
  const { userInfo } = useUserInfo();
  const [contactInfo, setContactInfo] = useState({
    userId: userInfo?.pageName ?? '',
    email: userInfo?.email ?? '',
    name: '',
    content: '',
  });

  const onChange = (value: string, name: string) => {
    setContactInfo({
      ...contactInfo,
      [name]: value,
    });
  };

  const onSubmit = () => {
    console.log(contactInfo);
    // TODO : 전송 구현하기
    alert('문의 전송');
  };

  const isDisabled = Object.values(contactInfo).some((item) => item === '');
  return (
    <StyledContactContent onSubmit={onSubmit}>
      <StyledTitle>문의하기</StyledTitle>
      <InputWrapper>
        <InputTitle>아이디</InputTitle>
        <Input
          placeholder="아이디를 입력해주세요 (필수)"
          name="userId"
          value={contactInfo.userId}
          onChange={({ target }) => onChange(target.value, target.name)}
        />
      </InputWrapper>
      <InputWrapper>
        <InputTitle>이메일</InputTitle>
        <Input
          placeholder="이메일을 입력해주세요 (필수)"
          name="email"
          value={contactInfo.email}
          onChange={({ target }) => onChange(target.value, target.name)}
        />
      </InputWrapper>
      <InputWrapper>
        <InputTitle>제목</InputTitle>
        <Input
          placeholder="문의사항 제목을 입력해주세요"
          name="name"
          value={contactInfo.name}
          onChange={({ target }) => onChange(target.value, target.name)}
        />
      </InputWrapper>
      <InputWrapper>
        <InputTitle> 문의 내용</InputTitle>
        <Textarea
          placeholder="문의할 내용을 입력해주세요"
          name="content"
          value={contactInfo.content}
          onChange={({ target }) => onChange(target.value, target.name)}
        />
      </InputWrapper>
      <InputWrapper>
        <Button disabled={isDisabled}>전송하기</Button>
      </InputWrapper>
    </StyledContactContent>
  );
};

export default ContactContent;
