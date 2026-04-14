<template>
  <div class="home-page">
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" anonymous />
    <link
      href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&family=Space+Grotesk:wght@400;500;600;700&display=swap"
      rel="stylesheet"
    />

    <!-- Hero Section -->
    <section class="hero">
      <div class="hero-bg-glow"></div>
      <div class="hero-content">
        <div class="hero-badge">AI-Powered Coding Platform</div>
        <h1 class="hero-title">
          让 AI 成为你的<br />
          <span class="hero-title-accent">编码搭档</span>
        </h1>
        <p class="hero-desc">
          YU-AI-Coder 是一个智能编码助手平台，帮助开发者自动生成高质量代码、
          智能调试错误、自动生成文档，大幅提升开发效率。
        </p>
        <div class="hero-actions">
          <a-button type="primary" size="large" class="btn-start" @click="goToRegister">
            免费开始使用
          </a-button>
          <a-button size="large" class="btn-learn" ghost @click="scrollToFeatures">
            了解更多
          </a-button>
        </div>
        <!-- Stats -->
        <div class="hero-stats">
          <div class="stat-item">
            <span class="stat-value">10x</span>
            <span class="stat-label">开发效率提升</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-value">50+</span>
            <span class="stat-label">编程语言支持</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-value">99%</span>
            <span class="stat-label">代码准确率</span>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="features">
      <div class="section-header">
        <h2 class="section-title">核心能力</h2>
        <p class="section-subtitle">三大核心功能，覆盖你的编码全流程</p>
      </div>
      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon feature-icon-green">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="16 18 22 12 16 6"></polyline>
              <polyline points="8 6 2 12 8 18"></polyline>
            </svg>
          </div>
          <h3 class="feature-name">智能代码生成</h3>
          <p class="feature-desc">根据自然语言描述自动生成高质量、可运行的代码，支持主流编程语言和框架。</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon feature-icon-blue">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"></circle>
              <line x1="12" y1="8" x2="12" y2="12"></line>
              <line x1="12" y1="16" x2="12.01" y2="16"></line>
            </svg>
          </div>
          <h3 class="feature-name">智能调试</h3>
          <p class="feature-desc">精准定位代码错误根因，提供修复建议和优化方案，让调试不再是难题。</p>
        </div>
        <div class="feature-card">
          <div class="feature-icon feature-icon-purple">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
              <polyline points="14 2 14 8 20 8"></polyline>
              <line x1="16" y1="13" x2="8" y2="13"></line>
              <line x1="16" y1="17" x2="8" y2="17"></line>
              <polyline points="10 9 9 9 8 9"></polyline>
            </svg>
          </div>
          <h3 class="feature-name">文档生成</h3>
          <p class="feature-desc">自动分析代码结构，生成清晰的项目文档和 API 说明，保持文档与代码同步。</p>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section">
      <div class="cta-glow"></div>
      <div class="cta-content">
        <h2 class="cta-title">准备好提升你的编码效率了吗？</h2>
        <p class="cta-desc">注册即可免费体验所有核心功能，无需信用卡</p>
        <a-button type="primary" size="large" class="btn-cta" @click="goToRegister">
          立即注册，免费体验
        </a-button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

const goToRegister = () => {
  router.push('/register');
};

const scrollToFeatures = () => {
  document.getElementById('features')?.scrollIntoView({ behavior: 'smooth' });
};
</script>

<style scoped>
/* ===== Fonts ===== */
.home-page {
  font-family: 'DM Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  color: #F8FAFC;
  overflow-x: hidden;
}

/* ===== Hero ===== */
.hero {
  position: relative;
  padding: 120px 24px 80px;
  display: flex;
  justify-content: center;
  text-align: center;
  overflow: hidden;
}

.hero-bg-glow {
  position: absolute;
  top: -200px;
  left: 50%;
  transform: translateX(-50%);
  width: 800px;
  height: 600px;
  background: radial-gradient(ellipse, rgba(34, 197, 94, 0.12) 0%, transparent 70%);
  pointer-events: none;
}

.hero-content {
  position: relative;
  max-width: 720px;
  z-index: 1;
}

.hero-badge {
  display: inline-block;
  padding: 6px 16px;
  border-radius: 100px;
  border: 1px solid #22C55E33;
  background: #22C55E12;
  color: #22C55E;
  font-size: 13px;
  font-weight: 500;
  letter-spacing: 0.5px;
  margin-bottom: 32px;
}

.hero-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 56px;
  font-weight: 700;
  line-height: 1.15;
  color: #F8FAFC;
  margin: 0 0 24px;
}

.hero-title-accent {
  background: linear-gradient(135deg, #22C55E, #4ADE80);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-desc {
  font-size: 18px;
  line-height: 1.7;
  color: #94A3B8;
  margin: 0 0 40px;
  max-width: 560px;
  margin-left: auto;
  margin-right: auto;
}

.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  margin-bottom: 64px;
}

.btn-start {
  height: 48px;
  padding: 0 32px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  color: #fff;
  box-shadow: 0 4px 20px rgba(34, 197, 94, 0.3);
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.btn-start:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 28px rgba(34, 197, 94, 0.4);
}

.btn-learn {
  height: 48px;
  padding: 0 32px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 10px;
  border: 1px solid #334155;
  color: #CBD5E1;
  background: transparent;
  cursor: pointer;
  transition: border-color 0.2s, color 0.2s;
}

.btn-learn:hover {
  border-color: #22C55E;
  color: #22C55E;
}

/* Stats */
.hero-stats {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.stat-value {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 32px;
  font-weight: 700;
  color: #F8FAFC;
}

.stat-label {
  font-size: 14px;
  color: #64748B;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background: #1E293B;
}

/* ===== Features ===== */
.features {
  padding: 80px 24px;
  max-width: 1000px;
  margin: 0 auto;
}

.section-header {
  text-align: center;
  margin-bottom: 56px;
}

.section-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 36px;
  font-weight: 700;
  color: #F8FAFC;
  margin: 0 0 12px;
}

.section-subtitle {
  font-size: 16px;
  color: #64748B;
  margin: 0;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.feature-card {
  background: #1E293B;
  border: 1px solid #272F42;
  border-radius: 16px;
  padding: 32px 28px;
  transition: transform 0.2s, border-color 0.3s;
  cursor: default;
}

.feature-card:hover {
  transform: translateY(-2px);
  border-color: #334155;
}

.feature-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.feature-icon-green {
  background: #22C55E18;
  color: #22C55E;
}

.feature-icon-blue {
  background: #3B82F618;
  color: #3B82F6;
}

.feature-icon-purple {
  background: #A855F718;
  color: #A855F7;
}

.feature-name {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 18px;
  font-weight: 600;
  color: #F1F5F9;
  margin: 0 0 10px;
}

.feature-desc {
  font-size: 14px;
  line-height: 1.7;
  color: #94A3B8;
  margin: 0;
}

/* ===== CTA Section ===== */
.cta-section {
  position: relative;
  padding: 80px 24px;
  text-align: center;
  overflow: hidden;
}

.cta-glow {
  position: absolute;
  bottom: -100px;
  left: 50%;
  transform: translateX(-50%);
  width: 600px;
  height: 400px;
  background: radial-gradient(ellipse, rgba(34, 197, 94, 0.08) 0%, transparent 70%);
  pointer-events: none;
}

.cta-content {
  position: relative;
  z-index: 1;
  max-width: 560px;
  margin: 0 auto;
}

.cta-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 32px;
  font-weight: 700;
  color: #F8FAFC;
  margin: 0 0 16px;
}

.cta-desc {
  font-size: 16px;
  color: #94A3B8;
  margin: 0 0 32px;
}

.btn-cta {
  height: 52px;
  padding: 0 40px;
  font-size: 17px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #22C55E, #16A34A);
  border: none;
  color: #fff;
  box-shadow: 0 4px 24px rgba(34, 197, 94, 0.3);
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.btn-cta:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 32px rgba(34, 197, 94, 0.4);
}

/* ===== Responsive ===== */
@media (max-width: 768px) {
  .hero {
    padding: 80px 20px 60px;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-desc {
    font-size: 16px;
  }

  .hero-actions {
    flex-direction: column;
    align-items: center;
  }

  .btn-start,
  .btn-learn {
    width: 100%;
    max-width: 280px;
  }

  .hero-stats {
    gap: 24px;
  }

  .stat-value {
    font-size: 24px;
  }

  .features-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .section-title {
    font-size: 28px;
  }

  .cta-title {
    font-size: 24px;
  }
}

@media (min-width: 769px) and (max-width: 1024px) {
  .hero-title {
    font-size: 44px;
  }

  .features-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
