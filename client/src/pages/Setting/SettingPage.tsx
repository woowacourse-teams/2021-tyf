import Template from '../../components/@atom/Template/Template';
import Transition from '../../components/@atom/Transition/Transition.styles';
import UserSettingForm from '../../components/Setting/UserSettingForm/UserSettingForm';

const SettingPage = () => {
  return (
    <Template>
      <Transition>
        <UserSettingForm />
      </Transition>
    </Template>
  );
};

export default SettingPage;
