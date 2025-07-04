export default ({ app }) =>{
    const userAgent = navigator.userAgent;
    const IsMobile = /Mobi|Android|iPhone/i.test(userAgent);

    if (IsMobile) {
        import('src/css/mobile.scss');
    } else {
        import('src/css/web.scss');
    }
}