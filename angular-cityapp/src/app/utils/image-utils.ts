export function getUrlFromDataUrl(imageData: any): string {
  if (!imageData) {
    return '';
  }

  const base64Data = imageData;
  const dataUrl = `data:image/png;base64,${base64Data}`;

  return dataUrl;
}
