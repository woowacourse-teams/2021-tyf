import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { SIZE } from '../../../constants/device';
import { useWindowResize } from '../../../utils/useWindowResize';
import Anchor from '../../@atom/Anchor/Anchor';
import Select from '../../@atom/Select/Select';
import {
  GuideListItem,
  GuideList,
  StyledNavigation,
  StyledTitle,
  StyledAnchor,
} from './GuideHeader.styles';

const guidePage = [
  { name: '회원가입 방법', href: '/guide/sign-up' },
  { name: '도네이션 하는 방법', href: '/guide/donator' },
  { name: '도네이션 받는 방법', href: '/guide/creator' },
  { name: '정산 수수료 안내', href: '/guide/fee' },
  { name: '문의하기', href: '/guide/contact' },
];

const GuideHeader = () => {
  const history = useHistory();
  const { windowWidth } = useWindowResize();
  const [currentGuidePage, setCurrentGuidePage] = useState(
    guidePage.find(({ href }) => href === window.location.pathname)?.name ?? guidePage[0].name
  );

  const onChangeGuidePage = (value: string) => {
    const routeTo = guidePage.find(({ name }) => name === value);

    if (!routeTo || routeTo.href === window.location.pathname) return;

    setCurrentGuidePage(value);
    history.push(routeTo.href);
  };

  const isDesktop = windowWidth > SIZE.DESKTOP_LARGE;

  return (
    <>
      <StyledTitle>고객센터</StyledTitle>
      <StyledNavigation>
        {isDesktop ? (
          <GuideList>
            {guidePage.map(({ name, href }, index) => (
              <GuideListItem key={index}>
                <StyledAnchor to={href} isCurrentContent={href === window.location.pathname}>
                  {name}
                </StyledAnchor>
              </GuideListItem>
            ))}
          </GuideList>
        ) : (
          <Select
            placeholder="페이지를 선택해주세요"
            selectOptions={guidePage.map(({ name }) => name)}
            value={currentGuidePage}
            onChange={onChangeGuidePage}
          />
        )}
      </StyledNavigation>
    </>
  );
};

export default GuideHeader;
