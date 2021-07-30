import { FormEvent, ChangeEvent } from 'react';

import Title from '../../@atom/Title/Title';
import {
  ProfileImgInputLabel,
  StyledUserSettingForm,
  ProfileImg,
  StyledSubTitle,
  NickNameInputContainer,
  IntroductionTextareaContainer,
} from './UserSettingForm.styles';
import DefaultUserProfile from '../../../assets/images/default-user-profile.png';
import Input from '../../@atom/Input/Input';
import Textarea from '../../@atom/Textarea/Textarea';
import Button from '../../@atom/Button/Button';
import useSettingForm from '../../../service/hooks/useSettingForm';
import useSetting from '../../../service/hooks/useSetting';

const UserSettingForm = () => {
  const { form, setNickname, setBio, setProfileImg } = useSettingForm();
  const { submit } = useSetting();

  const onChangeProfileImg = ({ target }: ChangeEvent<HTMLInputElement>) => {
    if (!target.files) return;

    setProfileImg(target.files[0]);
  };

  const onApply = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    submit(form);
  };

  return (
    <StyledUserSettingForm onSubmit={onApply}>
      <Title>설정</Title>
      <ProfileImgInputLabel>
        <ProfileImg src={form.profileImage ?? DefaultUserProfile} />
        <input
          type="file"
          onChange={onChangeProfileImg}
          accept="image/png,image/jpeg, image/jpg, image/gif"
          hidden
        />
      </ProfileImgInputLabel>

      <NickNameInputContainer>
        <StyledSubTitle>닉네임</StyledSubTitle>
        <Input
          value={form.nickname}
          onChange={({ target }) => setNickname(target.value)}
          placeholder="닉네임 입력하기"
        />
      </NickNameInputContainer>
      <IntroductionTextareaContainer>
        <StyledSubTitle>자기소개</StyledSubTitle>
        <Textarea
          value={form.bio}
          onChange={({ target }) => setBio(target.value)}
          placeholder="자기소개 입력하기"
        />
      </IntroductionTextareaContainer>
      <Button>적용하기</Button>
    </StyledUserSettingForm>
  );
};

export default UserSettingForm;
