import React, { useEffect, useRef, useState } from 'react';
import {
  StyledCarousel,
  LeftArrowButton,
  RightArrowButton,
  ContentContainer,
} from './Carousel.styles';

interface CarouselProps {
  pageCount: number;
  children: React.ReactNode;
  contentWidth?: number;
  className?: string;
}

const Carousel = ({ pageCount, children, contentWidth, className }: CarouselProps) => {
  const carouselRef = useRef<HTMLDivElement>(null);
  const [currentPage, setCurrentPage] = useState(0);

  const isFirstPage = currentPage <= 0;
  const isLastPage = currentPage >= pageCount - 1;

  const showPrevList = () => {
    if (isFirstPage) return;

    setCurrentPage(currentPage - 1);
  };

  const showNextList = () => {
    if (isLastPage) return;

    setCurrentPage(currentPage + 1);
  };

  useEffect(() => {
    if (!carouselRef.current) return;

    carouselRef.current.scroll({
      left: currentPage * carouselRef.current.clientWidth,
      behavior: 'smooth',
    });
  }, [currentPage]);

  return (
    <StyledCarousel>
      {!isFirstPage && <LeftArrowButton aria-label="left-arrow" onClick={showPrevList} />}
      <ContentContainer className={className} ref={carouselRef} contentWidth={contentWidth}>
        {children}
      </ContentContainer>
      {!isLastPage && <RightArrowButton aria-label="right-arrow" onClick={showNextList} />}
    </StyledCarousel>
  );
};

export default Carousel;
