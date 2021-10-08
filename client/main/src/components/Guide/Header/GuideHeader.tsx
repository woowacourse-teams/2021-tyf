import { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { SIZE } from '../../../constants/device';
import { useWindowResize } from '../../../utils/useWindowResize';
import Select from '../../@atom/Select/Select';
import { GuideListItem, GuideList, StyledNavigation, StyledTitle } from './GuideHeader.styles';

const guidePage = [
  { name: '회원가입 방법', href: '/' },
  { name: '도네이션 하는 방법', href: '/' },
  { name: '도네이션 받는 방법', href: '/guide/creator' },
  { name: '문의하기', href: '/' },
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
                <Link to={href}>{name}</Link>
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
