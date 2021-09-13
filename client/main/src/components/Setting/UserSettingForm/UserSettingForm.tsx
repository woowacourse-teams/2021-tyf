import { FormEvent, ChangeEvent, useEffect } from 'react';

import Title from '../../@atom/Title/Title.styles';
import {
  ProfileImgInputLabel,
  StyledUserSettingForm,
  ProfileImg,
  StyledSubTitle,
  NickNameInputContainer,
  IntroductionTextareaContainer,
  TextareaLengthLimit,
} from './UserSettingForm.styles';
import DefaultUserProfile from '../../../assets/images/default-user-profile.png';
import Textarea from '../../@atom/Textarea/Textarea';
import Button from '../../@atom/Button/Button.styles';
import useSetting from '../../../service//user/useSetting';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import useUserInfo from '../../../service//user/useUserInfo';
import useRegisterNickname from '../../../service/auth/useRegisterNickname';
import useSettingForm from '../../../service//user/useSettingForm';

const UserSettingForm = () => {
  const { userInfo } = useUserInfo();
  const { form, setNickname, setBio, setProfileImg } = useSettingForm();
  const {
    nickname,
    setNickname: _setNickname,
    nicknameErrorMessage,
    isValidNickName,
  } = useRegisterNickname();
  const { submit } = useSetting();

  const onChangeProfileImg = ({ target }: ChangeEvent<HTMLInputElement>) => {
    if (!target.files) return;

    setProfileImg(target.files[0]);
  };

  const onApply = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!confirm('정말로 변경사항을 저장하시겠습니까?')) return;

    submit(form);
  };

  useEffect(() => {
    _setNickname(form.nickname);
  }, [form.nickname]);

  const isSameNickname = userInfo?.nickname === nickname;

  return (
    <StyledUserSettingForm onSubmit={onApply}>
      <Title>설정</Title>
      <ProfileImgInputLabel>
        <ProfileImg alt="profile" src={form.profileImage || DefaultUserProfile} />
        <input
          type="file"
          aria-label="profile-img"
          onChange={onChangeProfileImg}
          accept="image/png,image/jpeg, image/jpg, image/gif"
          hidden
        />
      </ProfileImgInputLabel>

      <NickNameInputContainer>
        <StyledSubTitle>닉네임</StyledSubTitle>
        <ValidationInput
          aria-label="nickname"
          value={form.nickname}
          onChange={({ target }) => setNickname(target.value)}
          placeholder="닉네임 입력하기"
          isSuccess={isSameNickname || isValidNickName}
          successMessage="좋은 닉네임이네요!"
          failureMessage={nicknameErrorMessage}
        />
      </NickNameInputContainer>
      <IntroductionTextareaContainer>
        <StyledSubTitle>자기소개</StyledSubTitle>
        <Textarea
          aria-label="bio"
          value={form.bio}
          onChange={({ target }) => setBio(target.value)}
          placeholder="자기소개 입력하기"
          maxLength={500}
        />
        <TextareaLengthLimit>({form.bio.length} / 500)</TextareaLengthLimit>
      </IntroductionTextareaContainer>
      <Button disabled={!(isSameNickname || isValidNickName)}>적용하기</Button>
    </StyledUserSettingForm>
  );
};

export default UserSettingForm;
